import fs from 'fs'

export class Day1 {
  private readonly contents: string

  constructor() {
    this.contents = fs.readFileSync('resources/day1.txt').toString()
  }

  run1() {
    const nums = this.contents
      .split('\n')
      .map((line: string) => {
        return line.split('').filter((e) => e.match(/\d/))
      })
      .map((elems) => {
        return parseInt(elems[0]) * 10 + parseInt(elems[elems.length - 1])
      })

    return nums.reduce((acc, num) => acc + num, 0)
  }

  run2() {
    const numberNames = ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine']
    const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9].map((e) => `${e}`)
    const allNumNames = numberNames.concat(numbers)
    const nameMap: Record<string, number> = {
      one: 1,
      two: 2,
      three: 3,
      four: 4,
      five: 5,
      six: 6,
      seven: 7,
      eight: 8,
      nine: 9,
      1: 1,
      2: 2,
      3: 3,
      4: 4,
      5: 5,
      6: 6,
      7: 7,
      8: 8,
      9: 9
    }

    const findLastIndex: any = (line: string, sub: string, pos = -1) => {
      const posi = line.indexOf(sub, pos + 1)

      if (posi === -1) {
        return pos
      } else {
        return findLastIndex(line, sub, posi)
      }
    }

    const nums = this.contents.split('\n').map((line: string) => {
      const indexes = allNumNames.map((numName) => [
        line.indexOf(numName),
        findLastIndex(line, numName)
      ])

      // console.log(indexes)

      let smallestNum = 9999999
      let largestNum = 0

      indexes.forEach((idx, _index) => {
        if (idx[0] <= smallestNum && idx[0] !== -1) {
          smallestNum = idx[0]
        }
        if (idx[1] > largestNum) {
          largestNum = idx[1]
        }
      })

      // console.log([smallestNum, largestNum])

      let posOfSmallestNum = 0
      let posOfLargestNum = 0

      indexes.forEach((idx, index) => {
        if (idx[0] === smallestNum) {
          posOfSmallestNum = index
        }
        if (idx[1] === largestNum) {
          posOfLargestNum = index
        }
      })

      // console.log([posOfSmallestNum, posOfLargestNum])

      return [allNumNames[posOfSmallestNum], allNumNames[posOfLargestNum]]
    })

    const data = nums.map((elems) => {
      // console.log(elems)
      // console.log(nameMap[elems[0]] * 10 + nameMap[elems[1]])

      return nameMap[elems[0]] * 10 + nameMap[elems[1]]
    })

    return data.reduce((acc, num) => acc + num, 0)
  }
}
