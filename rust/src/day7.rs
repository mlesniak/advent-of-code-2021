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

pub fn part2() {
    let input: Vec<i32> = read_file("day7.txt").split(",").map(|x| x.parse().unwrap()).collect();
    // println!("{:?}", &input);

    let max = input.iter().max().unwrap();

    let mut min_score = i32::MAX;
    let mut solution = 0;
    for possible_position in (0..*max) {
        let ms = score_increment(&input, &possible_position);
        // println!("{} -> {}", &possible_position, &ms);
        if ms < min_score {
            solution = possible_position;
            min_score = ms;
        }
    }

    println!("Day 7 / Part 2 = {} with {}", solution, min_score);;
    // for n in 1..10 {
    //     println!("{} -> {}", n, sum(n));
    // }
}

fn score_increment(input: &Vec<i32>, pos: &i32) -> i32 {
    input.iter().map(|n| sum((n - pos).abs())).sum()
}

// Use n(n+1)/2 -- still this is fast enough
fn sum(n: i32) -> i32 {
    (1..=n).sum()
}

