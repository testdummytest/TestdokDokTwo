def label = "maven-${UUID.randomUUID().toString()}"
podTemplate(label: label,
serviceAccount: 'jenkins',
containers: [
	containerTemplate(name: 'maven', image: 'maven:3.8.8-eclipse-temurin-11-alpine', ttyEnabled: true, command: 'cat')
]) {
	node(label) {



		container('maven') {
			env.MAVEN_PROFILE = env.MAVEN_PROFILE ?: "dummy-profile" // default to non existing maven profile if not set as job parameter

			stage('Get sources') { checkout scm }

			stage('Tests') {
				try {
					sh "mvn -B clean test -Dselenium.browser=chrome -Dtest=ApiDoctorTests"
				// mvn "-P ${env.MAVEN_PROFILE} test -Dselenium.browser=chrome"
				}
				catch (Exception e) {
						System.out.println(e);
					}
				finally {
					echo "test is done"
				}
			}
		}
	}
}