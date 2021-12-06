use crate::util::read_file;

pub fn part1() {
    let max_days = 18;

    let mut fishes: Vec<i32> = read_file("day6.txt").split(",").map(|x| x.parse().unwrap()).collect();
    // println!("{:?}", fishes);

    let mut new_fish: Vec<i32> = Vec::new();
    for day in 0..max_days {
        println!("{}", day);
        println!("{}: {:?}", day, &fishes);
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

}