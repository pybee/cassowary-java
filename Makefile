
JAVAFILES = \
	AbstractConstraint.java \
	AbstractVariable.java \
	CassowaryError.java \
	Constraint.java \
	ConstraintNotFound.java \
	DummyVariable.java \
	EditConstraint.java \
	EditInfo.java \
	Expression.java \
	NonlinearExpression.java \
	ObjectiveVariable.java \
	RequiredFailure.java \
	SimplexSolver.java \
	SlackVariable.java \
	StayConstraint.java \
	Strength.java \
	Tableau.java \
	Util.java \
	Variable.java


java_JAVA = $(JAVAFILES)

all: AndroidCassowary.jar

test:
	javac org/pybee/cassowary/test/*.java
	java org.pybee.cassowary.test.Tests

clean:
	rm -f AndroidCassowary.jar Cassowary.jar
	find . -name "*.class" -exec rm {} \;

AndroidCassowary.jar: org/pybee/android/*.java Cassowary.jar
	javac -cp $(ANDROIDSDK)/platforms/android-14/android.jar:Cassowary.jar org/pybee/android/*.java
	jar -cvf AndroidCassowary.jar org/pybee/cassowary/*.class org/pybee/android/*.class

Cassowary.jar: org/pybee/cassowary/*.java
	javac org/pybee/cassowary/*.java
	jar -cvf Cassowary.jar org/pybee/cassowary/*.class
