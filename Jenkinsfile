node {
    git branch:'main',
    url:'https://github.com/hasnaaabdelrahman/Job-Application-Tracker.git'
    stage('build') {
        try{
            sh"echo 'build stage'"
        }catch(Exception e){
            sh"echo 'exception Found'"
            throw e
        }
    }
    stage('test') {
        if(env.BRANCH_NAME == "test") {
         sh"echo 'test stage'"
        }else {
         sh"echo 'skip test stage'"
        }

    }
}