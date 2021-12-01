use std::fs::File;
use std::io::Read;

pub fn day1() {
    let s = readFile("day1.txt");

    let lines: Vec<&str> = s.split("\n").collect();

    let mut numbers: Vec<i32> = Vec::new();
    for line in lines {
        let x: i32 = line.parse().unwrap();
        numbers.push(x);
    }

    let mut increase = 0;
    for i in 1..(numbers.len()) {
        if numbers[i] > numbers[i - 1] {
            increase += 1;
        }
    }
    println!("{}", increase);
}

fn readFile(path: &str) -> String {
    let mut f = File::open(path).unwrap();
    let mut s = String::new();
    f.read_to_string(&mut s);
    s
}

fn print_type_of<T>(_: &T) {
    println!("{}", std::any::type_name::<T>())
}