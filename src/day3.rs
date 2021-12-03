use std::collections::HashSet;

use crate::util::read_lines;

pub fn part1() {
    let lines = read_lines("day3.txt");
    println!("{:?}", lines);

    let i = 0;
    let mut gamma: Vec<String> = Vec::new();
    let mut epsilon: Vec<String> = Vec::new();

    let maxLen = lines[0].len();
    for i in 0..maxLen {
        println!("{}", i);
        let mut ones = 0;
        for line in &lines {
            let c = line.chars().nth(i).unwrap();
            match c {
                '0' => ones -= 1,
                '1' => ones += 1,
                _ => panic!("Unknown symbol {}", c),
            }
        }
        println!("-> {}", ones);
        if ones >= 0 {
            // Use vec<string>?
            gamma.push("1".to_string());
            epsilon.push("0".to_string());
        } else {
            gamma.push("0".to_string());
            epsilon.push("1".to_string());
        }
    }

    let gamma = isize::from_str_radix(&gamma.join(""), 2).unwrap();
    let epsilon = isize::from_str_radix(&epsilon.join(""), 2).unwrap();
    println!("gamma={:?}", gamma);
    println!("epsilon={:?}", epsilon);
    println!("result={}", gamma * epsilon);
}

pub fn part2() {
    let lines = read_lines("day3.txt");

    let i = 0;
    let mut o2 = HashSet::new();
    let mut co2 = HashSet::new();

    for line in &lines {
        o2.insert(line);
        co2.insert(line);
    }

    println!("{:?}", o2);

    let maxLen = lines[0].len();
    for bitPos in 0..maxLen {
        println!("bitPos={}", bitPos);
        let mut ones = 0;
        for line in &o2 {
            let c = line.chars().nth(bitPos).unwrap();
            match c {
                '0' => ones -= 1,
                '1' => ones += 1,
                _ => panic!("Unknown symbol {}", c),
            }
        }
        let mut filter: char;

        // Oxygen
        if o2.len() > 1 {
            if ones >= 0 {
                filter = '1';
            } else {
                filter = '0';
            }
            println!("  o2 filter={}", filter);

            let mut to = HashSet::new();
            for elem in o2 {
                if elem.chars().nth(bitPos).unwrap() == filter {
                    to.insert(elem);
                }
            }
            o2 = to;
            println!("  oxygen={:?}", o2);
        }

        // Co2
        if co2.len() > 1 {
            if ones >= 0 {
                filter = '0';
            } else {
                filter = '1';
            }
            println!("  co2 filter={}", filter);

            let mut to = HashSet::new();
            for elem in co2 {
                if elem.chars().nth(bitPos).unwrap() == filter {
                    to.insert(elem);
                }
            }
            co2 = to;
            println!("  co2={:?}", co2);
        }

        let o2 = isize::from_str_radix(&o2.iter().next().unwrap(), 2).unwrap();
        let co2 = isize::from_str_radix(&co2.iter().next().unwrap(), 2).unwrap();
        println!("o2={:?}", o2);
        println!("co2={:?}", co2);
        println!("result={}", o2 * co2);

    }

}
