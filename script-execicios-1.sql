CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- Tabela de Postagens
-- =========================
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    body TEXT,
    user_id INTEGER NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- =========================
-- Tabela de Seguidores
-- =========================
CREATE TABLE follows (
    following_user_id INTEGER NOT NULL,
    followed_user_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (following_user_id, followed_user_id),
    CONSTRAINT fk_following FOREIGN KEY (following_user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_followed FOREIGN KEY (followed_user_id) REFERENCES users (id) ON DELETE CASCADE
);






-- =========================
-- Inserts de Usuários
-- =========================
INSERT INTO users (username, role) VALUES
('alice', 'admin'),
('bob', 'user'),
('carol', 'user'),
('david', 'moderator'),
('eva', 'user');

-- =========================
-- Inserts de Postagens
-- =========================
INSERT INTO posts (title, body, user_id, status) VALUES
('Primeiro Post', 'Este é o primeiro post de Alice', 1, 'published'),
('Reflexões do dia', 'Hoje aprendi sobre SQL!', 2, 'draft'),
('Dicas de Estudo', 'Use o Pomodoro para manter o foco.', 3, 'published'),
('Atualizações do sistema', 'Novo recurso será lançado em breve.', 4, 'published'),
('Viagem incrível', 'Acabei de voltar de uma viagem maravilhosa!', 5, 'published'),
('SQL Avançado', 'Explorando joins e índices.', 1, 'published'),
('Segurança no sistema', 'Nunca compartilhe sua senha.', 2, 'published');

-- =========================
-- Inserts de Relações (Follows)
-- =========================
-- Alice segue Bob e Carol
INSERT INTO follows (following_user_id, followed_user_id) VALUES
(1, 2),
(1, 3);

-- Bob segue Alice e Eva
INSERT INTO follows (following_user_id, followed_user_id) VALUES
(2, 1),
(2, 5);

-- Carol segue David
INSERT INTO follows (following_user_id, followed_user_id) VALUES
(3, 4);

-- David segue Alice
INSERT INTO follows (following_user_id, followed_user_id) VALUES
(4, 1);

-- Eva segue Bob e Carol
INSERT INTO follows (following_user_id, followed_user_id) VALUES
(5, 2),
(5, 3);

--nome de usuário (username) e o total de curtidas que cada usuário recebeu em todas as suas postagens
select
	users.username,
	COUNT(follows.following_user_id) as total_curtidas
from
	users users
join posts posts on
	users.id = posts.user_id
left join follows follows on
	follows.followed_user_id = posts.user_id
group by
	users.username
order by
	total_curtidas desc;

-- Os 5 usuários com mais postagens nos últimos 30 dias
select
	users.username,
	COUNT(posts.id) as total_posts
from
	users users
join posts posts on
	users.id = posts.user_id
where
	posts.created_at >= NOW() - interval '30 days'
group by
	users.username
order by
	total_posts desc
limit 5;


-- Antes
SELECT alunoid, COUNT(*) 
FROM matriculaperiodo 
WHERE data >= '2024-01-01' 
GROUP BY alunoid;

-- Depois (usando índice por range de data)
CREATE INDEX idx_matriculaperiodo_data_alunoid
    ON matriculaperiodo (data, alunoid);

SELECT alunoid, COUNT(*) 
FROM matriculaperiodo 
WHERE semestre = '2024-1'
GROUP BY alunoid;














