<#ftl encoding="UTF-8">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome!</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f8f9fa;
        }
        .header {
            background-color: #4a6baf;
            color: white;
            padding: 20px;
            text-align: center;
            border-radius: 8px 8px 0 0;
        }
        .content {
            background-color: white;
            padding: 30px;
            border-radius: 0 0 8px 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            margin-top: 0;
        }
        .footer {
            text-align: center;
            margin-top: 30px;
            color: #6c757d;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Welcome!</h1>
</div>

<div class="content">
    <p>Hello, <strong>${username}</strong>!</p>

    <p>Thank you for registering with our service. You can now:</p>
    <ul>
        <li>Create and track your fitness goals</li>
        <li>Plan workouts and meals</li>
        <li>Save your favorite recipes and articles</li>
        <li>Monitor your progress</li>
    </ul>

    <p>Start your fitness journey today!</p>

    <p>Best regards,<br>The Development Team</p>
</div>

<div class="footer">
    <p>© 2025. All rights reserved.</p>
    <p>This email was sent automatically. Please do not reply.</p>
</div>
</body>
</html>
