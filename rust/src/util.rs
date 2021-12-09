use std::fs::File;
use std::io::Read;

pub fn read_file(path: &str) -> String {
    let mut f = File::open(path).unwrap();
    let mut s = String::new();
    match f.read_to_string(&mut s) {
        Ok(_) => s,
        Err(_) => panic!("Unable to read input file")
    }
}

pub fn read_lines(path: &str) -> Vec<String> {
    let s = read_file(path);
    let lines: Vec<String> = s.split("\n").map(|x| x.to_string()).collect();

    return lines;
}
