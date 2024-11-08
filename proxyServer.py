import http.server
import requests

class ProxyHTTPRequestHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        # Forward the request to localhost:9110
        response = requests.get(f'http://localhost:9110{self.path}')
        self.send_response(response.status_code)
        self.send_header('Content-Type', response.headers['Content-Type'])
        self.end_headers()
        self.wfile.write(response.content)

    def do_POST(self):
        # Forward POST requests
        content_length = int(self.headers['Content-Length'])
        post_data = self.rfile.read(content_length)
        response = requests.post(f'http://localhost:9110{self.path}', data=post_data)
        self.send_response(response.status_code)
        self.send_header('Content-Type', response.headers['Content-Type'])
        self.end_headers()
        self.wfile.write(response.content)

PORT = 9100
with http.server.HTTPServer(('', PORT), ProxyHTTPRequestHandler) as httpd:
    print(f"Serving proxy at http://localhost:{PORT}")
    httpd.serve_forever()
