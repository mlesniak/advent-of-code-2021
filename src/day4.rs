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

    fn mark(mut self: &mut Board, number: i32) {
        for row in &mut self.board {
            for bn in row {
                if bn.number == number {
                    (*bn).marked = true
                }
            }
        }
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
            println!("{:?}", b);
        }
    }
}

pub fn part2() {}
