import * as vscode from 'vscode';

export function activate(context: vscode.ExtensionContext) {

    console.log('BetterMake is now active');

    const build = vscode.commands.registerCommand('bettermake.build', () => {
        const editor = vscode.window.activeTextEditor;
        const terminal = vscode.window.createTerminal("BetterMake");
        terminal.show();
        terminal.sendText("java -jar BetterMake.jar");
    });
    context.subscriptions.push(build);

    const mkproj = vscode.commands.registerCommand("bettermake.mkproj", async () => {
        const projname = await vscode.window.showInputBox({
            placeHolder: 'Enter Project Name'
        });
        const lang = await vscode.window.showQuickPick(['C', 'C++', 'Objective-C', 'Objective-C++'], {
            placeHolder: 'Pick Project Language'
        });
        const projtype = await vscode.window.showQuickPick(['Multi File', 'Single File'], {
            placeHolder: 'Pick Project Type'
        });
        const mainFile = await vscode.window.showInputBox({
            placeHolder: 'Enter Main File Name ex. main.c'
        });
        if (projname && lang && projtype && mainFile) {
            let langArg = null;
            let projTypeArg = null;

            // Fix: Remove the 'let' inside the if blocks
            if (lang === 'C') { langArg = 'C'; }
            if (lang === 'C++') { langArg = 'CPP'; }
            if (lang === 'Objective-C') { langArg = 'OBJC'; }
            if (lang === 'Objective-C++') { langArg = 'OBJCPP'; }

            if (projtype === 'Multi File') { projTypeArg = 'MF'; }
            if (projtype === 'Single File') { projTypeArg = 'OF'; }

            // Make sure both variables are not null
            if (projTypeArg !== null && langArg !== null) {
                const terminal = vscode.window.createTerminal("BetterMake");
                terminal.show();
                terminal.sendText(`curl -fsSL https://tinyurl.com/BMAutoConf | bash -s  ${projname} ${mainFile} ${projTypeArg} ${langArg} `);
            }
        }
    });
    context.subscriptions.push(mkproj);
}

// This method is called when your extension is deactivated
export function deactivate() {}