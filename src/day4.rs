use std::process::exit;

use crate::util::read_lines;

#[derive(Debug)]
struct BoardNumber {
    number: i32,
    marked: bool,
}

#[derive(Debug)]
struct Board {
    board: Vec<Vec<BoardNumber>>,
}

impl Board {
    fn read(lines: &Vec<String>, start: usize) -> Board {
        let mut board: Vec<Vec<BoardNumber>> = Vec::new();
        for i in start..(start + 5) {
            let values: Vec<BoardNumber> = lines[i]
                .split(" ")
                .filter(|x| x.len() > 0)
                .map(|x| x.parse().unwrap())
                .map(|x| BoardNumber { number: x, marked: false })
                .collect();
            board.push(values);
        }

        Board { board }
    }

    fn mark(self: &mut Board, number: i32) {
        for row in &mut self.board {
            for bn in row {
                if bn.number == number {
                    (*bn).marked = true
                }
            }
        }
    }

    fn checkWin(self: &Board) -> bool {
        // Rows
        for row in &self.board {
            let mut allok = true;
            for bn in row {
                if bn.marked == false {
                    allok = false;
                    break;
                }
            }
            if !allok {
                continue;
            }
            return true;
        }

        for col in 0..5 {
            let mut allok = true;
            for row in 0..5 {
                if self.board.get(row).unwrap().get(col).unwrap().marked == false {
                    allok = false;
                    break;
                }
            }
            if !allok {
                continue;
            }
            return true;
        }

        return false;
    }

    fn score(self: &Board) -> i32 {
        let mut sum = 0;
        for row in &self.board {
           for bn in row {
               if !bn.marked {
                   sum += bn.number;
               }
           }
        }

        return sum
    }
}

pub fn part1() {
    let lines = read_lines("day4.txt");

    let numbers: Vec<i32> = lines[0].split(",").map(|x| x.parse().unwrap()).collect();
    // println!("{:?}", numbers);

    let num_boards = (lines.len() - 2) / 5;
    let mut boards: Vec<Board> = Vec::new();
    for start in (2..lines.len()).step_by(5 + 1) {
        let b = Board::read(&lines, start);
        boards.push(b);
    }
    // println!("{:?}", boards);

    for number in numbers {
        println!("{}", number);
        for board in &mut boards {
            board.mark(number);
        }

        for b in &boards {
            if b.checkWin() {
                println!("won!!! {:?}", b.score() *  number);
                exit(0)
            }
        }
    }
}

pub fn part2() {}
