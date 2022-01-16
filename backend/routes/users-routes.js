import express from 'express';
import pool from '../db.js';
import bcrypt from 'bcrypt';
import { authenticateToken } from '../middleware/authorization.js';
import { jwtTokens } from '../utils/jwt-helpers.js';
import jwt from 'jsonwebtoken';

const router = express.Router();

/* GET users listing. */
router.get('/', authenticateToken, async (req, res) => {
  try {
    const users = await pool.query('SELECT id_user, username, invite_code, avatar_url FROM users');
    res.json({ users: users.rows });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/', authenticateToken, async (req, res) => {
  try {
    const hashedPassword = await bcrypt.hash(req.body.password, 10);
    const newUser = await pool.query(
      'INSERT INTO users (username, password, invite_code, avatar_url) VALUES ($1,$2,$3, $4) RETURNING *'
      , [req.body.username, hashedPassword, req.body.username, 'https://via.placeholder.com/100']);
    res.json(jwtTokens(newUser.rows[0]));
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/my', authenticateToken, async (req, res) => {
  const authHeader = req.headers['authorization']; //Bearer TOKEN
  const token = authHeader && authHeader.split(' ')[1];
  const payload = jwt.decode(token)

  const users = await pool.query(
    'SELECT id_user, username, invite_code, avatar_url, bio FROM users where id_user = $1',
    [payload.id_user]
  )

  if (users.rows.length === 0) {
    res.status(404).send({ message: 'data not found' })
  }

  res.json(users.rows[0])
})

router.get('/:id', authenticateToken, async (req, res) => {
  const id_user = req.params.id

  const users = await pool.query(
    'SELECT id_user, username, invite_code, avatar_url, bio FROM users where id_user = $1',
    [id_user]
  )

  if (users.rows.length === 0) {
    res.status(404).send({ message: 'data not found' })
  }

  res.json(users.rows[0])
})

router.post('/change-bio', authenticateToken, async (req, res) => {
  const { id_user, bio } = req.body

  try {
    await pool.query(
      'UPDATE users SET bio = $1 WHERE id_user = $2',
      [bio, id_user]
    )

    res.json({ message: 'bio updated.' })
  } catch (error) {
    res.status(500).json({ message: error.message })
  }
})

router.post('/add-friend', authenticateToken, async (req, res) => {
  const authHeader = req.headers['authorization']; //Bearer TOKEN
  const token = authHeader && authHeader.split(' ')[1];
  const user = jwt.decode(token)
  const { invite_code } = req.body

  const friendQueryResult = await pool.query('SELECT id_user FROM users WHERE invite_code = $1', [invite_code])
  
  if (friendQueryResult.rows.length === 0) {
    res.status(404).send({ message: 'User not found.' })
  }

  const friend = friendQueryResult.rows[0]

  try {
    await pool.query(
      'INSERT INTO friendships VALUES (default, $1, $2)',
      [user.id_user, friend.id_user]
    )

    res.json({message: 'friend added.'})
  } catch (error) {
    res.status(500).json({ message: error.message })
  }
})

export default router;
