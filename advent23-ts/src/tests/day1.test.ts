import { Day1 } from '../day1'

describe('day 1', () => {
  test('day 1 - a', async () => {
    const res = new Day1().run1()
    console.log(res)
    expect(res).not.toBeNull()
  })

  test('day 1 - b', async () => {
    const res = new Day1().run2()
    console.log(res)
    expect(res).not.toBeNull()
  })
})
