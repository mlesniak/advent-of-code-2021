use std::fs::File;
use std::io::Read;

pub fn day1() {
    let mut f = File::open("day1.txt").unwrap();
    let mut s = String::new();
    f.read_to_string(&mut s);

    println!("{}", s);
}
