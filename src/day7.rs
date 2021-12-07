use std::i32::MAX;

use crate::util::read_file;

pub fn part1() {
    let input: Vec<i32> = read_file("day7.txt").split(",").map(|x| x.parse().unwrap()).collect();
    println!("{:?}", &input);

    let mut min_score = i32::MAX;
    let mut solution = 0;
    for possible_position in &input {
        let ms = score(&input, possible_position);
        if ms < min_score {
            solution = *possible_position;
            min_score = ms;
        }
    }

    println!("Day 7 / Part 1 = {} with {}", solution, min_score);;
}

fn score(input: &Vec<i32>, pos: &i32) -> i32 {
    input.iter().map(|n| (n - pos).abs()).sum()
}

pub fn part2() {}