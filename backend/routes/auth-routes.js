import express from 'express';
import jwt from 'jsonwebtoken';
import pool from '../db.js';
import bcrypt from 'bcrypt';
import { jwtTokens } from '../utils/jwt-helpers.js';

const router = express.Router();

router.post('/login', async (req, res) => {
  try {
    const { username, password } = req.body;
    const users = await pool.query('SELECT * FROM users WHERE username = $1', [username]);
    if (users.rows.length === 0) return res.status(401).json({error:"Account not found"});
    //PASSWORD CHECK
    const validPassword = await bcrypt.compare(password, users.rows[0].password);
    if (!validPassword) return res.status(401).json({error: "Incorrect password"});
    //JWT
    let tokens = jwtTokens(users.rows[0]);//Gets access tokens
    res.json(tokens);
  } catch (error) {
    res.status(401).json({error: error.message});
  }

});


export default router;