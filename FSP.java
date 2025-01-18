/*
 * Name(s):
 *     Yucheng Chen
 * 
 * Date:
 *     January 17, 2025
 * 
 * Course Code:
 *     ICS3U1-01
 * 
 * Title:
 *     F1 Array Racing - Professional Grade Interactive Array Learning System
 * 
 * Description:
 *     A sophisticated educational application that teaches Java array concepts through 
 *     Formula 1 racing themed examples and activities. The program implements 
 *     professional-grade software development practices including extensible 
 *     design, comprehensive logging, and robust error handling, while delivering 
 *     engaging interactive learning experiences through real-time visualizations 
 *     and dynamic content management.
 * 
 * Features:
 *     Core Educational Features:
 *     - Interactive array visualization using F1 racing context
 *     - Real-time lap time analysis with statistical computations
 *     - Animated race position tracking system using 2D arrays
 *     - Visual bubble sort demonstration with race data
 *     - Multi-format assessment system with detailed feedback
 * 
 *     Professional Development Features:
 *     - Comprehensive SLF4J logging system for debugging and monitoring
 *     - JSON-based content management for easy content updates
 *     - Extensible design allowing easy addition of new features
 *     - Robust exception handling with user-friendly error messages
 *     - Configurable UI components through external resources
 * 
 *     Technical Innovations:
 *     - Custom graphics rendering engine for smooth animations
 *     - Real-time data visualization with dynamic updates
 *     - Memory-efficient resource management system
 *     - Responsive design handling multiple screen sizes
 *     - Interactive charting system with live updates
 * 
 *     Advanced UI/UX Features:
 *     - Professional F1-themed design with custom graphics
 *     - Smooth animations for enhanced user experience
 *     - Intuitive navigation system with consistent layout
 *     - Dynamic content loading with progress indicators
 *     - Accessibility-conscious design elements
 * 
 * Major Skills:
 *     Software Architecture:
 *     - Object-Oriented Programming with modular design
 *     - Event-driven programming
 *     - MVC pattern implementation
 *     - Resource management and optimization
 * 
 *     Technical Implementation:
 *     - Advanced Java Swing GUI Development
 *     - Complex data structure manipulation
 *     - Custom graphics programming
 *     - File I/O and JSON processing
 *     - Multi-threaded animation system
 * 
 *     Professional Practices:
 *     - Logging and debugging
 *     - Error handling and validation
 *     - Clean code principles
 *     - Performance optimization
 * 
 * Areas of Concern:
 *     Resource Requirements:
 *     - Required files: background1.jpg, background2.jpg, questions.json
 *     - Minimum recommended screen resolution: 1600x900
 *     - Sufficient memory for graphics and animations
 * 
 *     Technical Dependencies:
 *     - SLF4J library for logging system
 *     - JSON processing capability
 *     - Java Runtime Environment with Swing support
 * 
 *     Extensibility Notes:
 *     - Question content can be modified via questions.json
 *     - Visual themes can be adjusted through resource files
 *     - Screen layouts are dynamically adjustable
 *     - New educational content can be added without code changes
 * 
 *     Performance Considerations:
 *     - Animation smoothness depends on system capabilities
 *     - Resource loading may take longer on slower systems
 *     - Graphics rendering requires decent GPU performance
 */

public class FSP {
	//main method calls the other methods
	public static void main(String[]args) {

		//Run the GUI - Main Menu Screen
		new MainMenuScreen();
		
	}
}