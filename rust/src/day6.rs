use std::collections::HashMap;

use crate::util::read_file;

pub fn part1() {
    let max_days = 80;

    let mut fishes: Vec<i64> = read_file("day6.txt").split(",").map(|x| x.parse().unwrap()).collect();
    // println!("{:?}", fishes);

    let mut new_fish: Vec<i64> = Vec::new();
    for day in 0..max_days {
        println!("{}", day);
        // println!("{}: {:?}", day, &fishes);
        for fish in &mut fishes {
            if *fish == 0 {
                *fish = 6 + 1;
                new_fish.push(8);
            }
            *fish -= 1;
        }
        for nf in &new_fish {
            fishes.push(*nf);
        }
        new_fish.clear();
    }
    println!("part1 = {}", fishes.len());
}

pub fn part2() {
    // Different approach: count number of lanternfish per day in a map, since
    // the single identities are not important.
    let max_days = 256;
    let mut lantern_counter = HashMap::new();

    let fishes: Vec<i64> = read_file("day6.txt").split(",").map(|x| x.parse().unwrap()).collect();
    for fish in fishes {
        let cur = lantern_counter.get(&fish).unwrap_or(&0);
        lantern_counter.insert(fish, cur + 1);
    }

    println!("{:?}", lantern_counter);
    for day in 0..max_days {
        // println!("\n===== After {} days: {:?}", day, &lantern_counter);
        let mut tmp: HashMap<i64, i64> = HashMap::new();
        for f in 0..=8 {
            if !lantern_counter.contains_key(&f) {
                continue
            }
            let v = lantern_counter.get(&f).unwrap();
            let fish = (&f, v);
            // println!("working on {:?}", fish.0);
            let mut lantern_day = *fish.0;
            let count = *fish.1;

            if lantern_day == 0 {
                // let previous_6 = lantern_counter.get(&7).unwrap_or(&0); // + tmp.get(&7).unwrap_or(&0);
                // println!("previous 7:{}", previous_6);
                tmp.insert(6, count);
                tmp.insert(8, count);
            } else if lantern_day == 7 {
                let previous_6 = tmp.get(&6).unwrap_or(&0); // + tmp.get(&7).unwrap_or(&0);
                // println!("previous 6:{}", previous_6);
                tmp.insert(6, count + previous_6);
            } else {
                tmp.insert(lantern_day - 1, count);
            }
            // println!("  tmp={:?}", tmp);
        }
        lantern_counter = tmp;
    }

    let sum: i64 = lantern_counter.values().sum();
    println!("Part 2 = {:?}", sum);
}