import express from 'express';
import jwt from 'jsonwebtoken';
import pool from '../db.js';
import { authenticateToken } from '../middleware/authorization.js';

const router = express.Router();

/* GET posts from friends */
router.get('/', authenticateToken, async (req, res) => {
  try {
    const authHeader = req.headers['authorization']; //Bearer TOKEN
    const token = authHeader && authHeader.split(' ')[1];
    const payload = jwt.decode(token)

    let query = 'select p.*, u.username, u.avatar_url, (select count(id_like) from likes where id_post = p.id_post) as likes, (select count(id_comment) as comments from comments where id_post = p.id_post) from posts p join users u using (id_user)';
    query += ' where id_user in (select id_friend from friendships where id_user = $1)';
    query += ' or id_user = $1';
    
    const posts = await pool.query(query, [payload.id_user]);
    return res.json({ posts: posts.rows });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
});

router.get('/:id', async (req, res) => {
  const id_post = req.params.id
  const posts = await pool.query(
    'select p.*, u.username, u.avatar_url from posts p join users u using (id_user) where id_post = $1',
    [id_post]
  )

  if (posts.rows.length === 0) {
    return res.status(404).json({error: 'data not found'})
  }

  let post = posts.rows[0]

  let likes = await pool.query('select * from likes where id_post = $1', [post.id_post])
  let comments = await pool.query(
    'select c.*, u.username from comments c join users u using (id_user) where id_post = $1',
    [post.id_post]
  )

  post = { ...post, likes: likes.rows, comments: comments.rows }
  return res.json(post)
});

router.post('/', authenticateToken, async (req, res) => {
  const { id_user, body } = req.body
  
  try {
    const posts = await pool.query(
      'INSERT INTO posts (id_user, body) VALUES ($1, $2) returning *',
      [id_user, body]
    )

    return res.json(posts.rows[0])
  } catch (error) {
    return res.status(500).json({error: error.message})
  }
})

export default router;
