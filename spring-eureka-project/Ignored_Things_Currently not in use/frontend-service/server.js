const express = require("express");
const path = require("path");
const cors = require("cors");
const fetch = require("node-fetch");

const app = express();
const PORT = 3001;
const BACKEND = process.env.BACKEND_BASE || 'http://localhost:8081';

app.use(cors());
app.use(express.json());
app.use(express.static(path.join(__dirname, "public")));

app.get("/", (req, res) => {
  res.sendFile(path.join(__dirname, "public/index.html"));
});

// Proxy for creating user (to avoid CORS issues)
app.post('/proxy/users/add', async (req, res) => {
  try {
    const r = await fetch(`${BACKEND}/users/add`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(req.body)
    });
    const text = await r.text();
    res.status(r.status).send(text);
  } catch (err) {
    console.error('proxy users/add error', err);
    res.status(502).send('proxy error');
  }
});

// Proxy for user login
app.post('/proxy/users/login', async (req, res) => {
  try {
    const r = await fetch(`${BACKEND}/users/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(req.body)
    });
    const text = await r.text();
    res.status(r.status).send(text);
  } catch (err) {
    console.error('proxy users/login error', err);
    res.status(502).send('proxy error');
  }
});

app.listen(PORT, () => {
  console.log(`Frontend running on http://localhost:${PORT}`);
});
