import pg from 'pg';
import dotenv from 'dotenv';

dotenv.config();

const { Pool } = pg;

let localPoolConfig = {
  user: 'postgres',
  password: 'postgres',
  host: 'localhost',
  port: '5432',
  database: 'friendconnect'
};

console.log("connecting to db", process.env.DATABASE_URL)
const poolConfig = process.env.DATABASE_URL ? {
  connectionString: process.env.DATABASE_URL
} : localPoolConfig;

const pool = new Pool(poolConfig);
export default pool;