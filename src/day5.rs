use crate::util::read_lines;

#[derive(Debug)]
struct Point {
    x: i32,
    y: i32,
}

impl Point {
    fn parse(coord: &str) -> Point {
        let numbers: Vec<_> = coord.split(",").map(|x| x.parse().unwrap()).collect();
        Point {
            x: numbers[0],
            y: numbers[1],
        }
    }
}

#[derive(Debug)]
struct Segment {
    start: Point,
    end: Point,
}

impl Segment {
    fn parse(line: &str) -> Segment {
        let segments: Vec<_> = line.split(" -> ").collect();
        Segment {
            start: Point::parse(segments[0]),
            end:  Point::parse(segments[1]),
        }
    }
}

pub fn part1() {
    let coordinates = read_lines("day5.txt");
    println!("{:?}", coordinates);

    for c in coordinates {
        let s = Segment::parse(&c);
        println!("{:?}", s);
    }
}