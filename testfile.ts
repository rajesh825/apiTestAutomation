import * as fs from 'fs';

// Define the interface for the CSV row
interface CSVRow {
    RecordTypeId: string;
    Order: number;
    Id: number;
    CompanyName: string;
    Street1: string;
    Street2: string;
    City: string;
    County: string;
    Country: string;
    PostCode: string;
}

// Function to parse CSV rows
function parseCSVRow(row: string): CSVRow {
    const [RecordTypeId, Order, Id, CompanyName, Street1, Street2, City, County, Country, PostCode] = row.split(',');
    return {
        RecordTypeId,
        Order: parseInt(Order),
        Id: parseInt(Id),
        CompanyName,
        Street1,
        Street2,
        City,
        County,
        Country,
        PostCode
    };
}

// Function to read CSV file and return an array of rows
function readCSVFile(filePath: string): Promise<CSVRow[]> {
    return new Promise((resolve, reject) => {
        fs.readFile(filePath, 'utf8', (err, data) => {
            if (err) {
                reject(err);
                return;
            }
            const rows = data.trim().split('\n').map(parseCSVRow);
            resolve(rows);
        });
    });
}

// Function to pick a random row from an array
function pickRandomRow<T>(array: T[]): T {
    const randomIndex = Math.floor(Math.random() * array.length);
    return array[randomIndex];
}

// Example usage
async function main() {
    try {
        const filePath = 'path/to/your/csv/file.csv';
        const rows = await readCSVFile(filePath);
        const randomRow = pickRandomRow(rows);
        console.log(randomRow);
    } catch (error) {
        console.error('Error occurred:', error);
    }
}

// Run the main function
main();
