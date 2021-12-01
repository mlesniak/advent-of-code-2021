use std::fs::File;
use std::io::Read;

pub enum Part {
    Part1,
    Part2
}

pub fn day1(part: Part) {
    let s = read_file("day1.txt");
    let lines: Vec<&str> = s.split("\n").collect();
    let mut numbers: Vec<i32> = Vec::new();
    for line in lines {
        let x: i32 = line.parse().unwrap();
        numbers.push(x);
    }

    match part {
        Part::Part1 => part1(&numbers),
        Part::Part2 => part2(&numbers)
    }
}

fn part2(numbers: &Vec<i32>) {
    let mut increase = 0;

    let mut windows: Vec<i32> = Vec::new();
    for i in 0..(numbers.len() - 2) {
        let sum = numbers[i] + numbers[i + 1] + numbers[i + 2];
        windows.push(sum);
    }

    for i in 1..(windows.len()) {
        if windows[i] > windows[i - 1] {
            increase += 1;
        }
    }

    println!("{}", increase);
}

fn part1(numbers: &Vec<i32>) {
    let mut increase = 0;
    for i in 1..(numbers.len()) {
        if numbers[i] > numbers[i - 1] {
            increase += 1;
        }
    }
    println!("{}", increase);
}

fn read_file(path: &str) -> String {
    let mut f = File::open(path).unwrap();
    let mut s = String::new();
    match f.read_to_string(&mut s) {
        Ok(_) => s,
        Err(_) => panic!("Unable to read input file")
    }
}
