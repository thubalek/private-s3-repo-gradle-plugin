package net.hubalek.gradle.s3privaterepo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.AwsCredentials
import java.net.URI

class S3PrivateRepo : Plugin<Project> {
    override fun apply(project: Project) {
        for (module in project.allprojects) {
            val s3Repo = module.setupProperty("s3Repo", "S3_REPO")
            val s3AccessKey = module.setupProperty("s3AccessKey", "S3_ACCESS_KEY")
            val s3SecretKey = module.setupProperty("s3SecretKey", "S3_SECRET_KEY")
            module.buildscript.repositories.maven {
                setupRepo(it, s3Repo, s3AccessKey, s3SecretKey)
            }
            module.repositories.maven {
                setupRepo(it, s3Repo, s3AccessKey, s3SecretKey)
            }
            module.extensions.extraProperties.set("s3PrivateRepo", module.repositories.maven {
                setupRepo(it, s3Repo, s3AccessKey, s3SecretKey)
            })

            println("S3 Private repo $s3Repo configured for project ${module.name}")
        }
    }

    private fun setupRepo(it: MavenArtifactRepository,
                          s3Repo: String,
                          s3AccessKey: String,
                          s3SecretKey: String
    ) {
        it.name = "s3PrivateRepo"
        it.url = URI.create(s3Repo)
        it.credentials(AwsCredentials::class.java) {
                it.accessKey = s3AccessKey
                it.secretKey = s3SecretKey
        }
    }

    private fun propertyOrEnvVar(project: Project, p: String, e: String): String {
        val projectProperty = project.findProperty(p)
        if (projectProperty is String? && !projectProperty.isNullOrBlank()) {
            return projectProperty.toString()
        }
        val systemProperties = System.getProperties()
        return if (systemProperties.containsKey(p)) {
            systemProperties.getProperty(p)
        } else {
            val value = System.getenv(e)
            if (!value.isNullOrBlank()) {
                value
            } else {
                throw RuntimeException("Unable to get value of property $p/$e")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun Project.setupProperty(systemPropertyKey: String,
                                      environmentVariable: String
    ): String {
        val propertyValue = propertyOrEnvVar(
                this,
                systemPropertyKey,
                environmentVariable
        )
        (this.properties as MutableMap<String,Any>)[systemPropertyKey] = propertyValue
        this.extensions.extraProperties.set(systemPropertyKey,
                propertyValue
        )
        return propertyValue
    }
}


