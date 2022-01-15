import express from 'express';
import pool from '../db.js';
import { authenticateToken } from '../middleware/authorization.js';

const router = express.Router();

/* GET users listing. */
router.get('/', async (req, res) => {
  try {
    const posts = await pool.query('SELECT * FROM posts');
    res.json({ posts: posts.rows });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/:id', async (req, res) => {
  const id_post = req.params.id
  const posts = await pool.query(
    'SELECT * FROM POSTS WHERE id_post = $1',
    [id_post]
  )

  if (posts.rows.length === 0) {
    res.status(404).json({error: 'data not found'})
    return
  }

  res.json(posts.rows[0])
});

router.post('/', authenticateToken, async (req, res) => {
  const { id_user, body } = req.body
  
  try {
    const posts = await pool.query(
      'INSERT INTO posts (id_user, body) VALUES ($1, $2) returning *',
      [id_user, body]
    )

    res.json(posts.rows[0])
  } catch (error) {
    res.status(500).json({error: error.message})
  }
})

export default router;
