from Tkinter import *
import runApp

class TimerApp:
    def __init__(self, parent):
        self.f = Frame(parent)
        self.f.pack()

        start_btn = Button(self.f, text='Start', command=self.start)
        start_btn.pack()

    def start(self):
        runApp.runApp()

def main():
    root = Tk()
    root.title('pennapps')
    app = TimerApp(root)

    root.mainloop()

if __name__ == '__main__':
    main()
