import hudson.model.*
import jenkins.model.*

def instance = Jenkins.getInstance()
instance.setSystemMessage("Welcome to Jenkins with Groovy configuration")

def jenkinsUrl = System.getenv("JENKINS_URL")
if (jenkinsUrl) {
    def config = JenkinsLocationConfiguration.get()
    config.setUrl(jenkinsUrl)
}

instance.save()


































