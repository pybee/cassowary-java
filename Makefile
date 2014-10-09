
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

all: CassowaryLayout.jar

test:
	javac org/pybee/cassowary/test/*.java
	java org.pybee.cassowary.test.Tests

clean:
	rm -f CassowaryLayout.jar
	find . -name "*.class" -exec rm {} \;

CassowaryLayout.jar: org/pybee/cassowary/*.java
	javac org/pybee/cassowary/*.java
	jar -cvf CassowaryLayout.jar org/pybee/cassowary/*.java