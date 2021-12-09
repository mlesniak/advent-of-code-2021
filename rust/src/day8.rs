use std::collections::HashMap;
use std::iter;
use std::ops::Index;

use itertools::Itertools;
use permute::permutations_of;

use crate::util::read_lines;

pub fn part1() {
    let lines: Vec<String> = read_lines("day8.txt");

    let mut digits_per_line: Vec<Vec<&str>> = lines.iter()
        .map(|line| line
            .split(" | ")
            .nth(1)
            .unwrap()
            .split(" ")
            .collect()
        )
        .collect();
    // println!("{:?}", digits_per_line);

    for line in &mut digits_per_line {
        line.retain(|elems|
            elems.len() == 2 || elems.len() == 4 || elems.len() == 3 || elems.len() == 7
        )
    }
    println!("{:?}", digits_per_line);
    let count: usize = digits_per_line.iter().map(|elems| elems.len()).sum();
    println!("Day 7 / Part 1 {:?}", count);
}

// Iterate over all ten digits.
// each digit has a mapping of existing positions, i.e.
//
// 0:
//   0, 1, 2, 4, 5, 6
// 1:
//   2, 5
// 4:
//   1,2,3,5
// 7:
//   0,2,5
//
// A valid configuration for these two would be
//
// 2 ->a
// 5 ->b
//
// 1: 2,5 => a,b
// 0: a,b
// 7: e,f,g =>
// 4: 1,2,3,5 => eafb
//
// Check if the string exists!
//
// number of possible configurations: 7! = 5040 -> brute force?
//
// 0 -> d
// 1 -> e
// 2 -> a
// 3 -> f
// 4 -> g
// 5 -> b
// 6 -> c
struct Configuration {
    //  0000
    // 1    2
    // 1    2
    //  3333
    // 4    5
    // 4    5
    //  6666
}


fn valid(config: &HashMap<i32, char>, goal: &Vec<String>) -> bool {
    let digits: Vec<Vec<i32>> = vec![
        vec![0, 1, 2, 4, 5, 6],     // 0
        vec![2, 5],                 // 1
        vec![0, 2, 3, 4, 6],        // 2
        vec![0, 2, 3, 5, 6],        // 3
        vec![1, 2, 3, 5],           // 4
        vec![0, 1, 3, 5, 6],        // 5
        vec![0, 1, 3, 4, 5, 6],     // 6
        vec![0, 2, 5],              // 7
        vec![0, 1, 2, 3, 4, 5, 6],  // 8
        vec![0, 1, 2, 3, 5, 6],     // 9
    ];

    // For each digit
    //   construct string
    //   check if it's inside.
    for d in &digits {
        // println!("{:?}", d);
        let mut s: Vec<&char> = d.iter()
            .map(|pos| config.get(pos).unwrap())
            .collect();
        s.sort();
        let k: String = s.iter().map(|uuc| *uuc).collect();
        // println!("{:?}", k);
        if !goal.contains(&k) {
            return false;
        }
    }

    true
}

fn convert(line: &str) -> Vec<String> {
    let mut res: Vec<String> = Vec::new();

    for part in line.split(" ") {
        // println!("{}", part);
        let mut cs: Vec<char> = part.chars().collect();
        cs.sort();
        let sorted: String = cs.iter().collect();
        res.push(sorted);
    }

    res
}

pub fn part2() {
    let mut sum = 0;
    let lines: Vec<String> = read_lines("day8.txt");

    for line in lines {
        let parts: Vec<&str> = line.split(" | ").collect();

        // let input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab";
        // let output = "cdfeb fcadb cdfeb cdbaf";

        let input = parts[0];
        let output = parts[1];

        let input = convert(input);
        let output: Vec<&str> = output.split(" ").collect();

        let mut found = false;
        let mut solution: HashMap<i32, char> = HashMap::new();
        for possible_solutions in permutations_of(&["a", "b", "c", "d", "e", "f", "g"]) {
            let ps: String = possible_solutions.map(|uus| *uus).collect();
            let sol = to_solution(&ps);

            let correct = valid(&sol, &input);
            if correct {
                found = true;
                println!("{:?}", sol);
                solution = sol;
                break;
            }
        }

        if !found {
            panic!("no solution found for {:?}", input);
        }

        let ov = to_digit(output[0], &solution) * 1000 +
            to_digit(output[1], &solution) * 100 +
            to_digit(output[2], &solution) * 10 +
            to_digit(output[3], &solution) * 1;
        println!("{}", ov);
        sum += ov;
    }

    println!("Day 8 Part 2 {}", sum);
    ;
}

fn to_digit(s: &str, solution: &HashMap<i32, char>) -> usize {
    let digits: Vec<Vec<i32>> = vec![
        vec![0, 1, 2, 4, 5, 6],     // 0
        vec![2, 5],                 // 1
        vec![0, 2, 3, 4, 6],        // 2
        vec![0, 2, 3, 5, 6],        // 3
        vec![1, 2, 3, 5],           // 4
        vec![0, 1, 3, 5, 6],        // 5
        vec![0, 1, 3, 4, 5, 6],     // 6
        vec![0, 2, 5],              // 7
        vec![0, 1, 2, 3, 4, 5, 6],  // 8
        vec![0, 1, 2, 3, 5, 6],     // 9
    ];


    let mut rev = HashMap::new();
    for kv in solution {
        rev.insert(kv.1, kv.0);
    }

    let mut v: Vec<i32> = Vec::new();
    for c in s.chars() {
        v.push(**rev.get(&c).unwrap());
    }
    v.sort();
    let d = digits.iter().position(|x| *x == v).unwrap();
    d
}

fn to_solution(s: &str) -> HashMap<i32, char> {
    let mut res = HashMap::new();

    let mut i = 0;
    for c in s.chars() {
        res.insert(i, c);
        i += 1
    }

    res
}