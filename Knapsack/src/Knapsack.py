class Item():
    def __init__(self, weight, value):
        self.weight = weight
        self.value = value
    def __str__(self):
        return str(self.weight) + " " + str(self.value)

def getItemsFromInput(numItems):
    items = [0]*numItems

    for i in range(numItems):
        line = input()
        line = line.split()
        
        items[i] = Item(int(line[0]),int(line[1]))

    return items

def printList(maxValues):
    for i in range(len(maxValues)):
      s = "[|"
      for w in range(len(maxValues[i])):
        s += (str(maxValues[i][w]) + "|")
      print(s + "]")   

def getMaxValue(items, capacity):
    maxValues = [[0 for i in range(capacity + 1)] for j in range(len(items) + 1)]

    for i in range(1,len(maxValues)):
      for w in range(len(maxValues[i])):
        if w == 0 or i == 0:
            maxValues[i][w] = 0
        else:
            item = items[i - 1]
            if item.weight > w:
                maxValues[i][w] = maxValues[i - 1][w]
            else:
                maxValues[i][w] = max([maxValues[i - 1][w], item.value + maxValues[i - 1][w - item.weight]])

    return maxValues[len(items)][capacity]

def main():
    numInstances = int(input())

    for i in range(numInstances):
      line = input().split(" ")
      numItems = int(line[0])
      capacity = int(line[1])

      items = getItemsFromInput(numItems)

      print(getMaxValue(items, capacity))


### Driver ###
#main()