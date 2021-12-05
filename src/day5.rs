use std::collections::{HashMap, HashSet};
use std::iter::Map;

use crate::util::read_lines;

#[derive(Debug, PartialEq, Eq, Hash)]
struct Point {
    x: i32,
    y: i32,
}

impl Point {
    fn parse(coord: &str) -> Point {
        let numbers: Vec<_> = coord
            .trim()
            .split(",")
            .map(|x| x.parse().unwrap())
            .collect();
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

    fn points_on_line(self: &Segment) -> Vec<Point> {
        let mut points = Vec::new();

        if self.start.x == self.end.x {
            // Vertical line.
            let ps = Segment::line(self.start.y, self.end.y);
            for p in ps {
                points.push(Point {
                    x: self.start.x,
                    y: p,
                })
            }
        } else {
            // Horizontal line.
            let ps = Segment::line(self.start.x, self.end.x);
            for p in ps {
                points.push(Point {
                    x: p,
                    y: self.start.y,
                })
            }
        }

        points
    }

    fn line(x1: i32, x2: i32) -> Vec<i32> {
        let mut points = Vec::new();

        if x1 < x2 {
            for i in x1..=x2 {
                points.push(i);
            }
        } else {
            for i in x2..=x1 {
                points.push(i);
            }
        }

        points
    }
}

pub fn part1() {
    let coordinates = read_lines("day5.txt");
    // println!("{:?}", coordinates);

    let mut lines = Vec::new();
    for c in coordinates {
        let s = Segment::parse(&c);
        // For now, only consider horizontal and vertical lines:
        // lines where either x1 = x2 or y1 = y2.
        if s.start.x == s.end.x || s.start.y == s.end.y {
            lines.push(s);
        }
    }

    // for line in lines {
    //     println!("{:?}", line);
    // }

    let mut overlapping_points: HashMap<Point, i32> = HashMap::new();
    for s in lines {
        let points = s.points_on_line();
        // println!("{:?} -> {:?}", s, points);
        for p in points {
            let count = overlapping_points.get(&p).unwrap_or(&0);
            // println!("{:?}", count);
            overlapping_points.insert(p, count + 1);
        }
    }

    let mut counter = 0;
    for count in overlapping_points.values() {
        if count > &1 {
            counter += 1
        }
        // println!("{:?}", count);
    }
    println!("Solution Day 5 / Part 1: {}", counter);
}