mvn package -DskipTests=true
java -jar target/cantstop-clone-1.0-SNAPSHOT-jar-with-dependencies.jar --optimize RandomContinuingProbabilityAIPlayer 10000 0.1 0.9 0.01