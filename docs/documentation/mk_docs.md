# `Website`
* [`Mk-Docs-Official`](https://www.mkdocs.org/)
* [`Material`](https://squidfunk.github.io/mkdocs-material/reference/)

# `Steps`

1. **Install Python**: Run the following command to install Python via Homebrew:
   ```sh
   brew install python
   ```

2. **Verify Installation**: After installation, verify that Python is correctly installed and accessible:
   ```sh
   python3 --version
   ```

3. **Create a Virtual Environment**: Use `python3` instead of `python` to create the virtual environment:
   ```sh
   python3 -m venv venv
   ```

4. **Activate it**:
   ```sh
   source venv/bin/activate
   ```

5. **Check it python is installed**:
   ```sh
   pip --version
   ```

6. **Install mkdocs**:
   ```sh
   pip install mkdocs-material
   ```

7. **Create mkdocs website**:
   ```sh
   mkdocs new .
   ```

9. **Run the website**:
   ```sh
   mkdocs serve
   ```

   Output:
   ```sh
   INFO    -  Building documentation...
   INFO    -  Cleaning site directory
   INFO    -  Doc file 'static_analysis.md' contains an absolute link '/config/detekt/detekt.yml', it was left as is.
   INFO    -  Documentation built in 0.03 seconds
   INFO    -  [20:42:24] Watching paths for changes: 'docs', 'mkdocs.yml'
   INFO    -  [20:42:24] Serving on http://127.0.0.1:8000/
   INFO    -  [20:42:31] Browser connected: http://127.0.0.1:8000/
   ```

   Now paste `http://127.0.0.1:8000/` in your browser and enter

# `Install Material`
_**Edit the mkdocs.yml**_
```sh
site_name: My Docs
theme:
  name: material
```

# `Useful Source`
* [`Tutorial-Documentation With MkDocs Material Theme`](https://www.youtube.com/watch?v=Q-YA_dA8C20&t=205s)