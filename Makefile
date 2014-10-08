
JAVAFILES = \
	AbstractVariable.java \
	DummyVariable.java \
	ObjectiveVariable.java \
	SlackVariable.java \
	Constraint.java \
	EditConstraint.java \
	EditInfo.java \
	EditOrStayConstraint.java \
	LinearConstraint.java \
	LinearEquation.java \
	LinearExpression.java \
	LinearInequality.java \
	StayConstraint.java \
	Strength.java \
	SymbolicWeight.java \
	Variable.java \
	Point.java \
	Tableau.java \
	SimplexSolver.java \
	ConstraintNotFound.java \
	CassowaryError.java \
	InternalError.java \
	NonlinearExpression.java \
	NotEnoughStays.java \
	RequiredFailure.java \
	TooDifficult.java \
	CL.java \
        ClTests.java \
	Set.java \
	sym.java \
	Timer.java


java_JAVA = $(JAVAFILES)

all: CassowaryLayout.jar

test:
	java org.pybee.cassowary.ClTests


CassowaryLayout.jar: org/pybee/cassowary/*.java
	javac org/pybee/cassowary/*.java
	jar -cvf CassowaryLayout.jar org/pybee/cassowary/*.java