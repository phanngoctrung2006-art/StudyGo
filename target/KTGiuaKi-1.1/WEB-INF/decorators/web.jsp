<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title"/></title>

    <!-- Google Fonts: Orbitron (Cyberpunk/Sci-Fi), Rajdhani (HUD/Tech Titles), Inter (Body) -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=Orbitron:wght@500;700;800;900&family=Rajdhani:wght@500;600;700&display=swap" rel="stylesheet">

    <!-- Bootstrap CSS & Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <!-- Giữ lại thẻ head riêng của từng trang con (nếu có) -->
    <sitemesh:write property="head"/>

    <!-- High-Tech Cyber Theme Styles -->
    <style>
        :root {
            --tech-bg: #070d1a;
            --tech-bg-card: rgba(13, 23, 44, 0.88);
            --tech-cyan: #00f2fe;
            --tech-blue: #38bdf8;
            --tech-purple: #818cf8;
            --tech-border: rgba(56, 189, 248, 0.28);
            --tech-glow: rgba(0, 242, 254, 0.35);
        }

        body.tech-theme {
            background-color: var(--tech-bg);
            color: #e2e8f0;
            font-family: 'Inter', system-ui, -apple-system, sans-serif;
            position: relative;
            min-height: 100vh;
            overflow-x: hidden;
        }

        /* Fixed High-Tech Cyber Circuit Background Canvas & Ambient Gradients */
        #tech-canvas-bg {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: -1;
            pointer-events: none;
            background: 
                radial-gradient(circle at 15% 15%, rgba(14, 165, 233, 0.16) 0%, transparent 45%),
                radial-gradient(circle at 85% 25%, rgba(99, 102, 241, 0.14) 0%, transparent 45%),
                radial-gradient(circle at 50% 85%, rgba(6, 182, 212, 0.12) 0%, transparent 50%),
                linear-gradient(rgba(56, 189, 248, 0.05) 1px, transparent 1px),
                linear-gradient(90deg, rgba(56, 189, 248, 0.05) 1px, transparent 1px);
            background-size: 100% 100%, 100% 100%, 100% 100%, 40px 40px, 40px 40px;
        }

        /* Custom Cyber Scrollbar */
        ::-webkit-scrollbar {
            width: 8px;
            height: 8px;
        }
        ::-webkit-scrollbar-track {
            background: #070d1a;
        }
        ::-webkit-scrollbar-thumb {
            background: #0284c7;
            border-radius: 4px;
        }
        ::-webkit-scrollbar-thumb:hover {
            background: #00f2fe;
        }

        /* Tech Brand & Headings */
        .tech-brand {
            font-family: 'Orbitron', monospace, sans-serif;
            letter-spacing: 1.5px;
            text-shadow: 0 0 12px rgba(0, 242, 254, 0.6);
        }
        h1, h2, h3, h4, h5, h6, .card-title {
            font-family: 'Rajdhani', sans-serif;
            letter-spacing: 0.5px;
        }

        /* Light/White Container Overrides for Dark Cyber theme */
        .bg-white, .bg-light {
            background-color: var(--tech-bg-card) !important;
            color: #f1f5f9 !important;
        }
        .border, .border-top, .border-bottom, .border-start, .border-end {
            border-color: rgba(56, 189, 248, 0.22) !important;
        }

        /* Glassmorphism Tech Cards Override */
        .card, .featured-card, .video-card, .cat-box {
            background: var(--tech-bg-card) !important;
            border: 1px solid var(--tech-border) !important;
            backdrop-filter: blur(12px) !important;
            -webkit-backdrop-filter: blur(12px);
            color: #f1f5f9 !important;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.45), 0 0 12px rgba(56, 189, 248, 0.08) !important;
            transition: all 0.25s ease-in-out !important;
            border-radius: 10px !important;
        }
        .card:hover, .featured-card:hover, .video-card:hover, .cat-box:hover {
            border-color: rgba(0, 242, 254, 0.6) !important;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.65), 0 0 20px rgba(0, 242, 254, 0.25) !important;
            transform: translateY(-3px);
        }

        .card-header {
            background: rgba(15, 29, 58, 0.88) !important;
            border-bottom: 1px solid var(--tech-border) !important;
            color: var(--tech-cyan) !important;
            font-weight: 700;
        }

        .card-footer {
            background: rgba(15, 29, 58, 0.6) !important;
            border-top: 1px solid var(--tech-border) !important;
        }

        /* Hero Banner Tech Styling */
        .hero-banner {
            background: linear-gradient(135deg, rgba(2, 132, 199, 0.28) 0%, rgba(99, 102, 241, 0.25) 100%) !important;
            border: 1px solid rgba(0, 242, 254, 0.4) !important;
            backdrop-filter: blur(14px) !important;
            box-shadow: 0 0 30px rgba(0, 242, 254, 0.15) !important;
            border-radius: 12px !important;
        }

        /* List Groups (Categories Sidebar) */
        .list-group-item {
            background: rgba(13, 23, 44, 0.75) !important;
            color: #cbd5e1 !important;
            border-color: rgba(56, 189, 248, 0.18) !important;
            transition: all 0.2s;
        }
        .list-group-item:hover {
            background: rgba(2, 132, 199, 0.25) !important;
            color: #38bdf8 !important;
            border-color: var(--tech-cyan) !important;
        }
        .list-group-item.active {
            background: linear-gradient(90deg, #0284c7 0%, #2563eb 100%) !important;
            border-color: var(--tech-cyan) !important;
            color: #fff !important;
            font-weight: bold;
            box-shadow: 0 0 15px rgba(2, 132, 199, 0.45);
        }

        /* Tables (Cart, Checkout Details, etc.) */
        .table {
            color: #e2e8f0 !important;
            background: transparent !important;
        }
        .table thead th {
            background: rgba(15, 29, 58, 0.9) !important;
            color: var(--tech-cyan) !important;
            border-bottom: 2px solid rgba(0, 242, 254, 0.4) !important;
            font-family: 'Rajdhani', sans-serif;
            font-size: 1.05rem;
            letter-spacing: 0.5px;
        }
        .table td, .table th {
            background: transparent !important;
            border-color: rgba(56, 189, 248, 0.15) !important;
            color: #e2e8f0 !important;
            vertical-align: middle;
        }
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: rgba(255, 255, 255, 0.02) !important;
        }

        /* Form Inputs & Selects */
        .form-control, .form-select {
            background-color: rgba(10, 18, 36, 0.85) !important;
            border: 1px solid rgba(56, 189, 248, 0.3) !important;
            color: #f8fafc !important;
            border-radius: 8px;
        }
        .form-control:focus, .form-select:focus {
            background-color: rgba(13, 23, 44, 0.95) !important;
            border-color: var(--tech-cyan) !important;
            box-shadow: 0 0 12px rgba(0, 242, 254, 0.4) !important;
            color: #fff !important;
        }
        .form-control::placeholder {
            color: #64748b !important;
        }

        /* Poster containers */
        .featured-poster, .video-poster-box {
            background: rgba(7, 13, 26, 0.6) !important;
            border: 1px solid rgba(56, 189, 248, 0.2) !important;
            border-radius: 6px;
        }

        /* Category Header Title */
        .category-header-title {
            background: rgba(15, 29, 58, 0.6) !important;
            border-left: 5px solid var(--tech-cyan) !important;
            color: #f1f5f9 !important;
            border-radius: 4px;
            box-shadow: inset 0 0 15px rgba(0, 242, 254, 0.05);
        }

        /* Text colors helper */
        .text-muted, .form-text {
            color: #94a3b8 !important;
        }
        .text-dark, .text-secondary {
            color: #f8fafc !important;
        }

        /* Primary and Outline Buttons */
        .btn-primary {
            background: linear-gradient(135deg, #0284c7 0%, #2563eb 100%) !important;
            border: 1px solid var(--tech-cyan) !important;
            box-shadow: 0 0 12px rgba(2, 132, 199, 0.4);
            color: #fff !important;
        }
        .btn-primary:hover {
            background: linear-gradient(135deg, #0369a1 0%, #1d4ed8 100%) !important;
            box-shadow: 0 0 18px rgba(0, 242, 254, 0.6);
            transform: translateY(-1px);
        }
        .btn-outline-primary {
            border-color: #38bdf8 !important;
            color: #38bdf8 !important;
        }
        .btn-outline-primary:hover {
            background-color: #0284c7 !important;
            border-color: var(--tech-cyan) !important;
            color: #fff !important;
            box-shadow: 0 0 12px rgba(0, 242, 254, 0.4);
        }

        /* Glowing Tech Badges */
        .badge.bg-primary {
            background: linear-gradient(135deg, #0284c7, #2563eb) !important;
            box-shadow: 0 0 8px rgba(2, 132, 199, 0.4);
        }
        .badge.bg-info {
            background: #0ea5e9 !important;
            color: #fff !important;
            box-shadow: 0 0 8px rgba(14, 165, 233, 0.4);
        }
        .badge.bg-warning {
            background: #f59e0b !important;
            color: #000 !important;
            box-shadow: 0 0 8px rgba(245, 158, 11, 0.4);
        }

        /* Pagination */
        .page-link {
            background-color: rgba(13, 23, 44, 0.8) !important;
            border-color: rgba(56, 189, 248, 0.2) !important;
            color: #38bdf8 !important;
        }
        .page-item.active .page-link {
            background-color: #0284c7 !important;
            border-color: var(--tech-cyan) !important;
            color: #fff !important;
            box-shadow: 0 0 10px rgba(0, 242, 254, 0.4);
        }
        .page-link:hover {
            background-color: rgba(2, 132, 199, 0.3) !important;
            color: var(--tech-cyan) !important;
        }

        /* Tech Navbar */
        .tech-navbar {
            background: rgba(8, 14, 28, 0.92) !important;
            border-bottom: 1px solid rgba(0, 242, 254, 0.25) !important;
            box-shadow: 0 4px 25px rgba(0, 0, 0, 0.6), 0 0 15px rgba(0, 242, 254, 0.1) !important;
            backdrop-filter: blur(16px);
            -webkit-backdrop-filter: blur(16px);
        }

        /* Tech Footer */
        .tech-footer {
            background: rgba(6, 11, 22, 0.95);
            border-top: 1px solid rgba(0, 242, 254, 0.25);
            box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.5);
            color: #94a3b8;
        }

        /* Alerts */
        .alert-success {
            background-color: rgba(16, 185, 129, 0.15) !important;
            border-color: rgba(16, 185, 129, 0.35) !important;
            color: #6ee7b7 !important;
        }
        .alert-info {
            background-color: rgba(14, 165, 233, 0.15) !important;
            border-color: rgba(14, 165, 233, 0.35) !important;
            color: #7dd3fc !important;
        }
        .alert-warning {
            background-color: rgba(245, 158, 11, 0.15) !important;
            border-color: rgba(245, 158, 11, 0.35) !important;
            color: #fde68a !important;
        }
        .alert-danger {
            background-color: rgba(239, 68, 68, 0.15) !important;
            border-color: rgba(239, 68, 68, 0.35) !important;
            color: #fca5a5 !important;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100 tech-theme">

    <!-- Interactive Cyber Tech Canvas -->
    <canvas id="tech-canvas-bg"></canvas>

    <!-- Header / Navbar -->
    <header>
        <%@ include file="/common/web/header.jsp"%>
    </header>

    <!-- Main Content -->
    <main class="container my-4 flex-grow-1">
        <sitemesh:write property="body"/>
    </main>

    <!-- Footer -->
    <footer class="mt-auto">
        <%@ include file="/common/web/footer.jsp"%>
    </footer>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Tech Particle Constellation Network JS Script -->
    <script>
        (function() {
            const canvas = document.getElementById('tech-canvas-bg');
            if (!canvas) return;
            const ctx = canvas.getContext('2d');
            let width, height;
            let particles = [];
            const particleCount = 70;
            let mouse = { x: null, y: null, radius: 140 };

            function resize() {
                width = canvas.width = window.innerWidth;
                height = canvas.height = window.innerHeight;
            }
            window.addEventListener('resize', resize);
            resize();

            window.addEventListener('mousemove', function(e) {
                mouse.x = e.x;
                mouse.y = e.y;
            });
            window.addEventListener('mouseout', function() {
                mouse.x = null;
                mouse.y = null;
            });

            class Particle {
                constructor() {
                    this.x = Math.random() * width;
                    this.y = Math.random() * height;
                    this.size = Math.random() * 2 + 1.2;
                    this.vx = (Math.random() - 0.5) * 0.7;
                    this.vy = (Math.random() - 0.5) * 0.7;
                    this.color = Math.random() > 0.4 ? 'rgba(0, 242, 254, ' : 'rgba(99, 102, 241, ';
                    this.baseAlpha = Math.random() * 0.5 + 0.3;
                }
                update() {
                    this.x += this.vx;
                    this.y += this.vy;
                    if (this.x < 0 || this.x > width) this.vx = -this.vx;
                    if (this.y < 0 || this.y > height) this.vy = -this.vy;

                    // Mouse interaction
                    if (mouse.x !== null) {
                        let dx = mouse.x - this.x;
                        let dy = mouse.y - this.y;
                        let dist = Math.sqrt(dx * dx + dy * dy);
                        if (dist < mouse.radius) {
                            let force = (mouse.radius - dist) / mouse.radius;
                            let angle = Math.atan2(dy, dx);
                            this.x -= Math.cos(angle) * force * 2;
                            this.y -= Math.sin(angle) * force * 2;
                        }
                    }
                }
                draw() {
                    ctx.beginPath();
                    ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
                    ctx.fillStyle = this.color + this.baseAlpha + ')';
                    ctx.shadowBlur = 8;
                    ctx.shadowColor = '#00f2fe';
                    ctx.fill();
                    ctx.shadowBlur = 0;
                }
            }

            for (let i = 0; i < particleCount; i++) {
                particles.push(new Particle());
            }

            function animate() {
                ctx.clearRect(0, 0, width, height);

                // Connect particles
                for (let a = 0; a < particles.length; a++) {
                    for (let b = a + 1; b < particles.length; b++) {
                        let dx = particles[a].x - particles[b].x;
                        let dy = particles[a].y - particles[b].y;
                        let dist = Math.sqrt(dx * dx + dy * dy);

                        if (dist < 115) {
                            let opacity = (1 - dist / 115) * 0.22;
                            ctx.beginPath();
                            ctx.strokeStyle = 'rgba(0, 242, 254, ' + opacity + ')';
                            ctx.lineWidth = 0.8;
                            ctx.moveTo(particles[a].x, particles[a].y);
                            ctx.lineTo(particles[b].x, particles[b].y);
                            ctx.stroke();
                        }
                    }
                }

                // Update & draw particles
                for (let i = 0; i < particles.length; i++) {
                    particles[i].update();
                    particles[i].draw();
                }

                requestAnimationFrame(animate);
            }
            animate();
        })();
    </script>
</body>
</html>