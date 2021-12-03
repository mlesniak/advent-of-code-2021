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

}
