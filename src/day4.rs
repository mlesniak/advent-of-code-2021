use crate::util::read_lines;

#[derive(Debug)]
struct Board {
    board: Vec<Vec<i32>>,
}

impl Board {
    fn read(lines: &Vec<String>, start: usize) -> Board {
        let mut board: Vec<Vec<i32>> = Vec::new();
        for i in start..(start + 5) {
            let values: Vec<i32> = lines[i]
                .split(" ")
                .filter(|x| x.len() > 0)
                .map(|x| x.parse().unwrap())
                .collect();
            board.push(values);
        }

        Board {board }
    }
}

pub fn part1() {
    let lines = read_lines("day4.txt");

    let numbers: Vec<i32> = lines[0].split(",").map(|x| x.parse().unwrap()).collect();
    println!("{:?}", numbers);

    let num_boards = (lines.len() - 2) / 5;
    let mut boards: Vec<Board> = Vec::new();
    for start in (2..lines.len()).step_by(5 + 1) {
        let b = Board::read(&lines, start);
        boards.push(b);
    }
    println!("{:?}", boards);
}

pub fn part2() {}
