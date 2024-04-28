from random import random
from Knapsack import printList
from Knapsack import Item

myDictionary = {"this": "dictionary", "uses": "hash", "functions": "."}
myDictionary[True] = "cool"
print(myDictionary[True])

if None:
    print("True")
else:
    pass

ordbok = {"jeg": "I", "er": "am", "katt":"cat"}
norsk = "jeg er katt"
ord = norsk.split()
english = ""
for o in ord:
    english += ordbok[o] + " "

print(english)

print(str(x := 3 and 2))
print(str(4 + 1.0))

print(int(random()*10) + 1)

printList([[1,2],[2]])

class TallItem(Item):
    def __init__(self, weight, value, height):
        super().__init__(weight,value)
        self.height = height
    def BMI(self):
        return self.weight / self.height

stick = TallItem(2,3,1)
print(str(stick))