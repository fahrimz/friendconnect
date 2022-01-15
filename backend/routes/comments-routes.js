
import express from 'express';
import pool from '../db.js';
import { authenticateToken } from '../middleware/authorization.js';

const router = express.Router();

router.post('/', authenticateToken, async (req, res) => {
    const { id_post, id_user, body } = req.body

    try {
        const comments = await pool.query(
            'INSERT INTO comments (id_post, id_user, body) VALUES ($1, $2, $3) returning *',
            [id_post, id_user, body]
        )

        res.json(comments.rows[0])
    } catch (error) {
        res.status(500).json({ error: error.message })
    }
})

export default router;
