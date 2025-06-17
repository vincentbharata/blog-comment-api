# blog-comment-api

### 🧩 **Challenge 8: Blog & Comments**

**Repository:** `blog-comment-api`

#### 📌 Product Requirement:

Penulis dapat membuat blog post dan pembaca bisa memberi komentar.

#### 🧠 Entities:

- `Post`: `id`, `title`, `content`, `author`
- `Comment`: `id`, `postId`, `name`, `message`

#### 🔗 API Endpoints:

1. `POST /posts` – Buat blog post
2. `POST /posts/{id}/comments` – Tambah komentar
3. `GET /posts/{id}/comments` – Lihat komentar pada blog
4. `GET /comments/{id}` – Detail komentar

#### ✅ Validation:

- Judul minimal 5 karakter
- Komentar tidak boleh kosong
