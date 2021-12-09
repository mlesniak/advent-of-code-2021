use crate::util::read_file;

pub fn part1() {
    let mut grid: Vec<Vec<u32>> = Vec::new();
    let file = read_file("day9.txt");
    for line in file.split("\n") {
        let mut row: Vec<u32> = Vec::new();
        line
            .chars()
            .map(|x| x.to_digit(10).unwrap())
            .for_each(|n| row.push(n));
        grid.push(row);
    }

    let mut sum = 0;
    for y in 0..grid.len() {
        for x in 0..(grid.get(0).unwrap().len()) {
            println!();
            if is_lowest_point(&grid, x, y) {
                let cur = grid.get(y).unwrap().get(x).unwrap();
                sum += cur + 1;
            }
            // println!("{} {}", x, y);
        }
    }
    println!("Day 9 / Part 1={:?}", sum);
}

// Not perfect that we cast here...
fn size(grid: &Vec<Vec<u32>>) -> (i32, i32) {
    let height = grid.len();
    let width = grid.get(0).unwrap().len();
    (i32::try_from(width).unwrap(), i32::try_from(height).unwrap())
}

fn is_lowest_point(grid: &Vec<Vec<u32>>, x: usize, y: usize) -> bool {
    let value = grid.get(y).unwrap().get(x).unwrap();
    let (w, h) = size(grid);

    let x = i32::try_from(x).unwrap();
    let y = i32::try_from(y).unwrap();

    println!("*** {}/{}", x, y);

    let directions = vec![(-1, 0), (1, 0), (0, -1), (0, 1)];
    for (dx, dy) in directions {
        // for dy in 0i32..=2 {
        // for dx in 0i32..=2 {
        //     println!("dx={} dy={}", dx-1, dy-1);
        //     let s = dy.abs() + dx.abs();
        //     println!("s={}", s);
        //     if (s == 2) || s== 0 || s==4 {
        //         continue;
        //     }

        // println!("{}/{} -> {}/{}", x, y, x + dx - 1, y + dy - 1);
        let mut nx = x + dx;
        let mut ny = y + dy;

        // if nx < 0 {
        //     nx = w - 1;
        // }
        // if nx == w {
        //     nx = 0;
        // }
        // if ny < 0 {
        //     ny = h - 1;
        // }
        // if ny == h {
        //     ny = 0;
        // }
        println!("nx/ny {}/{}", nx, ny);

        if nx < 0 {
            continue;
        }
        if nx == w {
            continue;
        }
        if ny < 0 {
            continue;
        }
        if ny == h {
            continue;
        }

        println!("looking for {}/{}", nx, ny);
        let pos_value = grid.get(usize::try_from(ny).unwrap()).unwrap().get(usize::try_from(nx).unwrap()).unwrap();
        if pos_value <= value {
            println!("higher, aborting");
            return false;
        }
        // }
    }

    println!("!!! minimal point: {} at {}/{}", value, x, y);
    true
}