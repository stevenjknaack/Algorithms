### Steven J Knaack; CS577; RandSAT.py ###

from random import getrandbits

class Clause():
  def __init__(self, literals):
    self.literals = literals

  def evaluate(self, assignment):
    value = 0
    for i in range(3):
      lit = self.literals[i]
      if lit >= 0:
        litassign = int(assignment[lit])
      else:
        litassign = not int(assignment[lit * -1])
      if litassign == 1:
        return 1
    return 0
    

class Rand3SAT():
  def __init__(self, numLiterals, numClauses, clauses):
    self.numLiterals = numLiterals
    self.numClauses = numClauses
    self.clauses = clauses
    self.luckyNumber = int(len(clauses) * (7/8))

  def genRand3SATFromInput():
    numLiterals =  int(input()) 
    numClauses = int(input())

    clauses = [None]*numClauses
    for i in range(numClauses):
      literals = input().split(" ")
      for j in range(3):
        literals[j] = int(literals[j])
      clauses[i] = Clause(literals)
    
    return Rand3SAT(numLiterals, numClauses, clauses)
      
  def generateAssignment(self):
    assignment = format(getrandbits(self.numLiterals), "b").zfill(self.numLiterals)
    return "b" + assignment

  def evaluateClauses(self, assignment):
    numTrue = 0
    for i in range(self.numClauses):
      clause = self.clauses[i]
      numTrue += clause.evaluate(assignment)
    return numTrue

def main():
  instance = Rand3SAT.genRand3SATFromInput()
  clausesSatisfied = 0
  while (clausesSatisfied < instance.luckyNumber):
    assignment = instance.generateAssignment() 
    clausesSatisfied = instance.evaluateClauses(assignment)
    #print(str(clausesSatisfied))
  
  assignmentStr = ""
  for i in range(1,len(assignment)):
    assign = assignment[i]
    if assign == "1":
      assignmentStr += "1 "
    else:
      assignmentStr += "-1 " 
  
  print(assignmentStr.rstrip())
  

### driver ###
main()



