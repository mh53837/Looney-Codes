const express = require("express");
const { createProxyMiddleware } = require("http-proxy-middleware");
require("dotenv").config();
const path = require("path")

const app = express();

// Configuration
const { PORT } = process.env;
const { HOST } = process.env;
const { API_BASE_URL } = process.env;

// Proxy
app.use(
        "/api",
        createProxyMiddleware({
                target: API_BASE_URL,
                changeOrigin: true,
        })
);

app.use(express.static(path.join(__dirname, 'build')))

app.listen(PORT, HOST, () => {
        console.log(`Starting Proxy at ${HOST}:${PORT}`);
});

app.get("*", async (req, res) => {
        res.sendFile(path.join(__dirname, 'build', 'index.html'))
}
);