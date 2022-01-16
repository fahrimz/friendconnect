import express from 'express';
import pool from '../db.js';
import { authenticateToken } from '../middleware/authorization.js';

const router = express.Router();

router.post('/toggle', authenticateToken, async (req, res) => {
  const { id_post, id_user } = req.body

  // check if user has liked the post before
  const likes = await pool.query(
    'SELECT * FROM likes where id_post = $1 and id_user = $2',
    [id_post, id_user]
  )

  const userLiked = likes.rows.length > 0

  try {

    if (userLiked) {
      // remove like if user has liked
      const like = likes.rows[0]

      await pool.query(
        'DELETE FROM likes where id_like = $1',
        [like.id_like]
      )

      res.json({ message: 'post unliked.' })
    } else {
      await pool.query(
        'INSERT INTO likes values (default, $1, $2)',
        [id_post, id_user]
      )

      res.json({ message: 'post liked.' })
    }
  } catch (error) {
    res.status(500).json({ error: error.message })
  }
})

export default router;
