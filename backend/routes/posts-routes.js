import express from 'express';
import pool from '../db.js';
import { authenticateToken } from '../middleware/authorization.js';

const router = express.Router();

/* GET users listing. */
router.get('/', async (req, res) => {
  try {
    const query = 'select p.*, u.username, u.avatar_url, (select count(id_like) from likes where id_post = p.id_post) as likes, (select count(id_comment) as comments from comments where id_post = p.id_post) from posts p join users u using (id_user)';
    const posts = await pool.query(query);
    res.json({ posts: posts.rows });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/:id', async (req, res) => {
  const id_post = req.params.id
  const posts = await pool.query(
    'select p.*, u.username, u.avatar_url from posts p join users u using (id_user) where id_post = $1',
    [id_post]
  )

  if (posts.rows.length === 0) {
    res.status(404).json({error: 'data not found'})
    return
  }

  let post = posts.rows[0]

  let likes = await pool.query('select * from likes where id_post = $1', [post.id_post])
  let comments = await pool.query(
    'select c.*, u.username from comments c join users u using (id_user) where id_post = $1',
    [post.id_post]
  )

  post = { ...post, likes: likes.rows, comments: comments.rows }
  res.json(post)
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
