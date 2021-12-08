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

pub fn part2() {

}