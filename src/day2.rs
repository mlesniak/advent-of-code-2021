use crate::util;

pub fn part2() {
    let s = util::read_file("day2.txt");
    let lines: Vec<_> = s.split("\n").collect();

    let mut aim = 0;
    let mut x = 0;
    let mut y = 0;

    for line in lines {
        let parts: Vec<_> = line.split_ascii_whitespace().collect();
        println!("{:?}", parts);

        let arg: u32 = parts[1].parse().unwrap();

        match parts[0] {
            "forward" => {
                x = x + arg;
                y = y + arg * aim;
            },
            "down" => aim = aim + arg,
            "up" => aim = aim - arg,
            _ => panic!("not found: {}", parts[0])
        }
    }

    let res = x * y;
    println!("{}", res);

}

pub fn part1() {
    let s = util::read_file("day2.txt");
    let lines: Vec<_> = s.split("\n").collect();

    let mut x = 0;
    let mut y = 0;

    for line in lines {
        let parts: Vec<_> = line.split_ascii_whitespace().collect();
        println!("{:?}", parts);

        let arg: u32 = parts[1].parse().unwrap();

        match parts[0] {
            "forward" => x = x + arg,
            "down" => y = y + arg,
            "up" => y = y - arg,
            _ => panic!("not found: {}", parts[0])
        }
    }

    let res = x * y;
    println!("{}", res);
}

