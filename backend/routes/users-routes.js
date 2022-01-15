import express from 'express';
import pool from '../db.js';
import bcrypt from 'bcrypt';
import {authenticateToken} from '../middleware/authorization.js';
import { jwtTokens } from '../utils/jwt-helpers.js';

const router = express.Router();

/* GET users listing. */
router.get('/',authenticateToken, async (req, res) => {
  try {
    const users = await pool.query('SELECT id_user, username, invite_code, avatar_url FROM users');
    res.json({users : users.rows});
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});

router.post('/', async (req, res) => {
  try {
    const hashedPassword = await bcrypt.hash(req.body.password, 10);
    const newUser = await pool.query(
      'INSERT INTO users (username, password, invite_code, avatar_url) VALUES ($1,$2,$3, $4) RETURNING *'
      , [req.body.username, hashedPassword, req.body.username, 'https://via.placeholder.com/100']);
    res.json(jwtTokens(newUser.rows[0]));
  } catch (error) {
    res.status(500).json({error: error.message});
  }
});

export default router;
