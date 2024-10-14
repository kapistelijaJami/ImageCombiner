import requests
from PIL import Image
import os

def download_images(base_url, start_x, end_x, start_y, end_y, output_dir):
    """
    Downloads images from a grid defined by indices and saves them with specific filenames.

    Args:
        base_url (str): Base URL of the image format (e.g., "example.com/path/").
        start_x (int): Starting index for x-coordinates in the grid.
        end_x (int): Ending index for x-coordinates (exclusive).
        start_y (int): Starting index for y-coordinates in the grid.
        end_y (int): Ending index for y-coordinates (exclusive).
        output_dir (str): Directory to save downloaded images.
    """
  
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    for y in range(start_y, end_y):
        for x in range(start_x, end_x):
            # Construct image URL using indices (ex. https://example.com/path/x/y.jpg)
            image_url = f"{base_url}{x}/{y}.jpg"

            # Download the image
            image_response = requests.get(image_url)
            if image_response.status_code == 200:
                image_data = image_response.content

                # Create filename with format img_x-y.jpg
                filename = f"img_{x}-{y}.jpg"
                filepath = os.path.join(output_dir, filename)

                # Save the image
                with open(filepath, 'wb') as f:
                    f.write(image_data)

                print(f"Downloaded image: {filename}")
            else:
                print(f"Error downloading image: {image_url} (Status code: {image_response.status_code})")

# Example usage
base_url = "https://example.com/path/"
start_x = 0  # Start is inclusive
end_x = 20  # End is exclusive
start_y = 0
end_y = 20
output_dir = "images"

download_images(base_url, start_x, end_x, start_y, end_y, output_dir)